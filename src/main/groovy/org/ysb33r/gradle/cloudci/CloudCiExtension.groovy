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
import org.gradle.api.Action
import org.gradle.api.Project

/**
 * @author Schalk W. Cronjé
 */
@CompileStatic
class CloudCiExtension {

    CloudCiExtension(Project p) {
        this.project = p
    }

    void any(@DelegatesTo(Project) Closure cfg) {
        configureConditionally CiEnvironments.Service.values(), cfg
    }

    void appveyor(@DelegatesTo(Project) Closure cfg) {
        configureConditionally 'appveyor', cfg
    }

    void circleci(@DelegatesTo(Project) Closure cfg) {
        configureConditionally 'circleci', cfg
    }

    void travisci(@DelegatesTo(Project) Closure cfg) {
        configureConditionally 'travisci', cfg
    }

    void jenkinsci(@DelegatesTo(Project) Closure cfg) {
        configureConditionally 'jenkinsci', cfg
    }

    void gitlabci(@DelegatesTo(Project) Closure cfg) {
        configureConditionally 'gitlabci', cfg
    }

    void any(Action<Project> cfg) {
        configureConditionally CiEnvironments.Service.values(), cfg
    }

    void appveyor(Action<Project> cfg) {
        configureConditionally 'appveyor', cfg
    }

    void circleci(Action<Project> cfg) {
        configureConditionally 'circleci', cfg
    }

    void travisci(Action<Project> cfg) {
        configureConditionally 'travisci', cfg
    }

    void jenkinsci(Action<Project> cfg) {
        configureConditionally 'jenkinsci', cfg
    }

    void gitlabci(Action<Project> cfg) {
        configureConditionally 'gitlabci', cfg
    }

    private void configureConditionally(final String envVar, Action<Project> cfg) {
        configureConditionally System.getenv(CiEnvironments.Service[envVar]) != null, cfg
    }

    private void configureConditionally(final Iterable<String> envVars, Action<Project> cfg) {
        configureConditionally(
            envVars.any { System.getenv(it) != null },
            cfg
        )
    }

    private void configureConditionally(final String envVar, Closure cfg) {
        configureConditionally System.getenv(CiEnvironments.Service[envVar]) != null, cfg
    }

    private void configureConditionally(final Iterable<String> envVars, Closure cfg) {
        configureConditionally(
            envVars.any { System.getenv(it) != null },
            cfg
        )
    }

    private void configureConditionally(boolean canConfigure, Action<Project> cfg) {
        if (canConfigure) {
            cfg.execute(project)
        }
    }

    private void configureConditionally(boolean canConfigure, Closure cfg) {
        if (canConfigure) {
            Closure exe = (Closure) cfg.clone()
            exe.delegate = project
            exe.call()
        }
    }

    private final Project project

}
