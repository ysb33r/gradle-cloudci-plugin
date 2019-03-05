/*
 * ============================================================================
 * (C) Copyright Schalk W. Cronje 2015 - 2019
 *
 * This software is licensed under the Apache License 2.0
 * See http://www.apache.org/licenses/LICENSE-2.0 for license details
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 * ============================================================================
 */
package org.ysb33r.gradle.cloudci

import groovy.transform.CompileStatic
import groovyx.net.http.HttpBuilder
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestDescriptor
import org.gradle.api.tasks.testing.TestResult

import static groovyx.net.http.ContentTypes.JSON
import static groovyx.net.http.HttpBuilder.configure
import static org.ysb33r.gradle.cloudci.internal.AppveyorUtils.mapTestResult

/** Adds a test reporter to each instance of a Test task.
 *
 * @since 2.3
 */
@CompileStatic
class AppveyorTestReporterPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        if (System.getenv('APPVEYOR_API_URL')) {

            project.tasks.findAll { it instanceof Test }.each { Task t ->
                addReporter((Test) t)
            }

            project.tasks.whenTaskAdded { Task t ->
                if (t instanceof Test) {
                    addReporter((Test) t)
                }
            }
        }
    }

    private void addReporter(Test task) {
        AppveyorReporterExtension reporterExtension = task.extensions.create('appveyor-test-reporter', AppveyorReporterExtension, task)

        task.afterTest { TestDescriptor td, TestResult result ->
            reporterExtension.sendTestResultMessage(td, result)
        }
    }

    static class AppveyorReporterExtension {
        AppveyorReporterExtension(Test task) {
            testTask = task
            http = configure {
                request.uri = System.getenv('APPVEYOR_API_URL')
                request.contentType = JSON[0]
            }
        }

        void sendTestResultMessage(TestDescriptor td, TestResult result) {
            final Map<String, String> postable = [:]
            try {
                String errorStackTrace
                if (result.exception) {
                    Writer w = new StringWriter()
                    result.exception.printStackTrace(new PrintWriter(w))
                    errorStackTrace = w.toString()
                    w.close()
                } else {
                    errorStackTrace = ''
                }

                postable.putAll([
                    testName            : td.name,
                    testFramework       : 'JUnit or TestNG',
                    fileName            : td.className ?: '(n/a)',
                    outcome             : mapTestResult(result.resultType),
                    durationMilliseconds: "${(result.endTime - result.startTime)}".toString(),
                    ErrorMessage        : result.exceptions*.message.join("\n"),
                    ErrorStackTrace     : errorStackTrace,
                    StdOut              : '',
                    StdErr              : ''
                ])

                post(postable)

            } catch (Throwable t) {
                testTask.project.logger.error "Could not create message for Appveyor because: ${t.message} (${postable})", t
            }
        }

        private void post(final Map<String, String> postable) {
            try {
                http.post {
                    request.uri.path = '/api/tests'
                    request.body = postable
                    response.failure {
                        testTask.project.logger.warn('Could not post test report to Appveyor')
                    }
                }
            } catch (java.net.ConnectException e) {
                testTask.project.logger.warn "Could not connect to ${System.getenv('APPVEYOR_API_URL')}"
            }

        }

        private final Test testTask
        private final HttpBuilder http
    }
}
