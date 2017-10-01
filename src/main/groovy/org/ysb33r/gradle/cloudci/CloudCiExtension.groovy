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

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import org.gradle.api.Project

/**
 * @author Schalk W. Cronjé
 */
@CompileStatic
class CloudCiExtension {

    CloudCiExtension(Project p) {
        this.project = p
    }

    void any(Closure cfg) {
        configureConditionally ENVIRONMENTS.values(),cfg
    }

    void appveyor(Closure cfg) {
        configureConditionally 'appveyor',cfg
    }

    void circleci(Closure cfg) {
        configureConditionally 'circleci',cfg
    }

    void travisci(Closure cfg) {
        configureConditionally 'travisci',cfg
    }

    void jenkinsci(Closure cfg) {
        configureConditionally 'jenkinsci',cfg
    }

    void gitlabci(Closure cfg) {
        configureConditionally 'gitlabci',cfg
    }

    @CompileDynamic
    private configureConditionally (final String envVar,Closure cfg) {
        configureConditionally System.getenv(ENVIRONMENTS[envVar]) != null, cfg
    }

    @CompileDynamic
    private configureConditionally (final Iterable<String> envVars,Closure cfg) {
        configureConditionally(
            envVars.any { System.getenv(it) != null } ,
            cfg
        )
    }

    @CompileDynamic
    private configureConditionally (boolean canConfigure,Closure cfg) {
        if(canConfigure) {
            Closure exe = cfg.clone()
            exe.delegate = project
            exe.call()
        }
    }
    private Project project

    private static Map<String,String> ENVIRONMENTS = [
        appveyor  : 'APPVEYOR',
        circleci  : 'CIRCLECI',
        jenkinsci : 'JENKINS_URL',
        travisci  : 'TRAVIS',
        gitlabci  : 'GITLAB_CI'
    ]
}
