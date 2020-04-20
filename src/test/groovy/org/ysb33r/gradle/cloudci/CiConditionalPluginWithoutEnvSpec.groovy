/*
 * ============================================================================
 * (C) Copyright Schalk W. Cronje 2015 - 2020
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

import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification
import spock.lang.Unroll

class CiConditionalPluginWithoutEnvSpec extends Specification {

    @Unroll
    def 'Testing #env.id run with no environment'() {
        assert System.getenv()[env.envVars.first()] == null

        when:
        def project = ProjectBuilder.builder().build()
        project.apply plugin: 'org.ysb33r.cloudci'
        project.ci."${env.id}" {
            ext {
                foo = 'bar'
            }
        }
        project.ci.no_ci {
            ext {
                foo2 = 'bar2'
            }
        }

        project.evaluate()

        then:
        project.ext.hasProperty('foo') == null
        project.ext.foo2 == 'bar2'

        where:
        env << CiEnvironment.values()
    }

}