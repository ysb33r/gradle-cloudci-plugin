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

/** Environmental variables for each CI.
 *
 * @since 2.3
 */
@CompileStatic
class CiEnvironments {
    static final Map<String,String> Service = [
        appveyor  : 'APPVEYOR',
        circleci  : 'CIRCLECI',
        jenkinsci : 'JENKINS_URL',
        travisci  : 'TRAVIS',
        gitlabci  : 'GITLAB_CI'
    ]
}
