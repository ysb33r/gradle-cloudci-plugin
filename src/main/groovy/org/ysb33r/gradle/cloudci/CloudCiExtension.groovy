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
import org.gradle.api.Action
import org.gradle.api.Project

import static org.ysb33r.gradle.cloudci.CiEnvironment.*

/**
 * @author Schalk W. Cronjé
 */
@CompileStatic
@Deprecated
class CloudCiExtension {

    CloudCiExtension(Project p) {
        this.project = p
    }

    @Deprecated
    void no_ci(@DelegatesTo(Project) Closure cfg) {
        boolean found = CiEnvironment.values().any { CiEnvironment envVars ->
            envVars.detected
        }
        configureConditionally( !found, cfg )
    }

    @Deprecated
    void any_ci(@DelegatesTo(Project) Closure cfg) {
        boolean found = CiEnvironment.values().any { CiEnvironment envVars ->
            envVars.detected
        }
        configureConditionally( found, cfg )
    }

    @Deprecated
    void any(@DelegatesTo(Project) Closure cfg) {
        project.logger.warn('Use of `cloudci.any` is deprecated. Use `ci.any_ci` instead.')
        any_ci cfg
    }

    @Deprecated
    void appveyor(@DelegatesTo(Project) Closure cfg) {
        configureConditionally APPVEYOR, cfg
    }

    @Deprecated
    void circleci(@DelegatesTo(Project) Closure cfg) {
        configureConditionally CIRCLE_CI, cfg
    }

    @Deprecated
    void travisci(@DelegatesTo(Project) Closure cfg) {
        configureConditionally TRAVIS_CI, cfg
    }

    @Deprecated
    void jenkinsci(@DelegatesTo(Project) Closure cfg) {
        configureConditionally JENKINS_CI, cfg
    }

    @Deprecated
    void gitlabci(@DelegatesTo(Project) Closure cfg) {
        configureConditionally GITLAB_CI, cfg
    }

    @Deprecated
    void bamboo(@DelegatesTo(Project) Closure cfg) {
        configureConditionally BAMBOO, cfg
    }

    @Deprecated
    void no_ci(Action<Project> cfg) {
        deprecationMsg('no_ci')
        boolean found = CiEnvironment.values().any { CiEnvironment envVars ->
            envVars.detected
        }
        configureConditionally( !found, cfg )
    }

    @Deprecated
    void any_ci(Action<Project> cfg) {
        deprecationMsg('any_ci')
        boolean found = CiEnvironment.values().any { CiEnvironment envVars ->
            envVars.detected
        }
        configureConditionally( found, cfg )
    }

    @Deprecated
    void any(Action<Project> cfg) {
        project.logger.warn('Use of `cloudci.any` is deprecated. Use `ci.any_ci` instead.')
        boolean found = CiEnvironment.values().any { CiEnvironment envVars ->
            envVars.detected
        }
        configureConditionally( found, cfg )
    }

    @Deprecated
    void appveyor(Action<Project> cfg) {
        configureConditionally APPVEYOR, cfg
    }

    @Deprecated
    void circleci(Action<Project> cfg) {
        configureConditionally CIRCLE_CI, cfg
    }

    @Deprecated
    void travisci(Action<Project> cfg) {
        configureConditionally TRAVIS_CI, cfg
    }

    @Deprecated
    void jenkinsci(Action<Project> cfg) {
        configureConditionally JENKINS_CI, cfg
    }

    @Deprecated
    void gitlabci(Action<Project> cfg) {
        configureConditionally GITLAB_CI, cfg
    }

    @Deprecated
    void bamboo(Action<Project> cfg) {
        configureConditionally BAMBOO, cfg
    }

    private void configureConditionally(CiEnvironment env, Action<Project> cfg) {
        deprecationMsg(env.id)
        configureConditionally(env.detected,cfg)
    }

    private void configureConditionally(CiEnvironment env, Closure cfg) {
        deprecationMsg(env.id)
        configureConditionally(env.detected,cfg)
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

    private void deprecationMsg(final String part) {
        project.logger.warn("Use of `cloudci.${part}` is deprecated and will be removed in a future version. Use `ci.${part}` instead.")

    }

    private final Project project

}
