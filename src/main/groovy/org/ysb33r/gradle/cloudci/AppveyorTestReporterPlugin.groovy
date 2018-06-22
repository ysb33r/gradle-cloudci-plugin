/*
 * ============================================================================
 * (C) Copyright Schalk W. Cronje 2015 - 2018
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

            project.tasks.withType(Test) { Test t ->
                addReporter(t)
            }

            project.tasks.whenTaskAdded { Task t ->
                if (t instanceof Test) {
                    addReporter((Test) t)
                }
            }
        }
    }

    static void addReporter(Test test) {
        test.afterTest { TestDescriptor td, TestResult result ->

            Map<String, String> postable = [:]

            try {

                String errorStackTrace
                if (result.exception) {
                    Writer w = new StringWriter()
                    result.exception.printStackTrace(new PrintWriter(w))
                    errorStackTrace = w.toString()
                } else {
                    errorStackTrace = ''
                }

                postable.putAll([
                    testName            : td.name,
                    testFramework       : 'JUnit or TestNG',
                    fileName            : td.className ?: '(n/a)',
                    outcome             : mapTestResult(result.resultType),
                    durationMilliseconds: "${(result.endTime - result.startTime)}".toString(),
                    ErrorMessage        : result.exception?.message ?: '',
                    ErrorStackTrace     : errorStackTrace,
                    StdOut              : '',
                    StdErr              : ''
                ])

                HttpBuilder http = configure {
                    request.uri = System.getenv('APPVEYOR_API_URL')
                    request.contentType = JSON[0]
                }

                http.post {
                    request.uri.path = '/api/tests'
                    request.body = postable
                    response.failure {
                        test.project.logger.warn('Could not post test report to Appveyor')
                    }
                }
            } catch(java.net.ConnectException e) {
                test.project.logger.warn "Could not connect to ${System.getenv('APPVEYOR_API_URL')}"
            } catch (Throwable t) {
                test.project.logger.error "Could not create message for Appveyor because: ${t.message} (${postable})",  t
            }
        }
    }
}
