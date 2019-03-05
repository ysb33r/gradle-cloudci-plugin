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

/** Environmental variables for each CI.
 *
 * @since 2.3
 */
@CompileStatic
enum CiEnvironment {
    APPVEYOR('appveyor', 'APPVEYOR'),
    BAMBOO('bamboo', 'bamboo_build_working_directory', 'bamboo.build.working.directory'),
    CIRCLE_CI('circleci', 'CIRCLECI'),
    CODESHIP( 'codeship', 'CI_NAME'),
    DRONE('drone', 'DRONE'),
    GITLAB_CI('gitlabci', 'GITLAB_CI'),
    JENKINS_CI('jenkinsci', 'JENKINS_URL'),
    TRAVIS_CI('travisci', 'TRAVIS')

    final List<String> envVars
    final String id

    boolean getDetected() {
        envVars.any { String var ->
            System.getenv(var) != null
        }
    }

    @Override
    String toString() {
        id
    }

    private CiEnvironment(String id, String... envVars) {
        this.id = id
        this.envVars = envVars as List
        if (this.envVars.empty) {
            throw new IllegalStateException('Cannot have a CiEnvironment with no environmental variables')
        }
    }
}
