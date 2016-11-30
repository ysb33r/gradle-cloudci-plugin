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
 * @author Schalk W. Cronjé
 */
class CloudCiConditionalPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.ext {
            appveyor = { Closure cl ->
                if(System.getenv('APPVEYOR')) {
                    Closure exe = cl.clone()
                    exe.delegate = project
                    exe.call()
                }
            }

            travisci = { Closure cl ->
                if(System.getenv('TRAVIS')) {
                    Closure exe = cl.clone()
                    exe.delegate = project
                    exe.call()
                }
            }

            circleci = { Closure cl ->
                if(System.getenv('CIRCLECI')) {
                    Closure exe = cl.clone()
                    exe.delegate = project
                    exe.call()
                }
            }
        }
    }
}
