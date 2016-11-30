/*
 * ============================================================================
 * (C) Copyright Schalk W. Cronje 2015 - 2016
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
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 */
class CloudCiConditionalPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {

        def executor = { String envVar, Closure cl ->
            if(System.getenv(envVar)) {
                Closure exe = cl.clone()
                exe.delegate = project
                exe.call()
            }
        }

        [ appveyor  : 'APPVEYOR',
          travisci  : 'TRAVIS',
          circleci  : 'CIRCLECI',
          jenkinsci : 'JENKINS_URL'
        ].each { String ci, String env ->
            project.ext.set ci, executor.curry(env)
        }
    }
}
