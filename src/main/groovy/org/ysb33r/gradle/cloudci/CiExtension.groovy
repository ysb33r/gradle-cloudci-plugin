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
class CiExtension {

    CiExtension(Project p) {
        this.project = p
    }

    void no_ci(@DelegatesTo(Project) Closure cfg) {
        boolean found = CiEnvironment.values().any { CiEnvironment envVars ->
            envVars.detected
        }
        configureConditionally(!found, cfg)
    }

    void no_ci(Action<Project> cfg) {
        boolean found = CiEnvironment.values().any { CiEnvironment envVars ->
            envVars.detected
        }
        configureConditionally(!found, cfg)
    }

    void any_ci(@DelegatesTo(Project) Closure cfg) {
        boolean found = CiEnvironment.values().any { CiEnvironment envVars ->
            envVars.detected
        }
        configureConditionally(found, cfg)
    }

    void any_ci(Action<Project> cfg) {
        boolean found = CiEnvironment.values().any { CiEnvironment envVars ->
            envVars.detected
        }
        configureConditionally(found, cfg)
    }

    void appveyor(@DelegatesTo(Project) Closure cfg) {
        configureConditionally APPVEYOR, cfg
    }

    void appveyor(Action<Project> cfg) {
        configureConditionally APPVEYOR, cfg
    }

    void bamboo(@DelegatesTo(Project) Closure cfg) {
        configureConditionally BAMBOO, cfg
    }

    void bamboo(Action<Project> cfg) {
        configureConditionally BAMBOO, cfg
    }

    void circleci(@DelegatesTo(Project) Closure cfg) {
        configureConditionally CIRCLE_CI, cfg
    }

    void circleci(Action<Project> cfg) {
        configureConditionally CIRCLE_CI, cfg
    }

    void codebuild(@DelegatesTo(Project) Closure cfg) {
        configureConditionally CODEBUILD, cfg
    }

    void codebuild(Action<Project> cfg) {
        configureConditionally CODEBUILD, cfg
    }

    void codeship(@DelegatesTo(Project) Closure cfg) {
        configureConditionally CODESHIP, cfg
    }

    void codeship(Action<Project> cfg) {
        configureConditionally CODESHIP, cfg
    }

    void drone(@DelegatesTo(Project) Closure cfg) {
        configureConditionally DRONE, cfg
    }

    void drone(Action<Project> cfg) {
        configureConditionally DRONE, cfg
    }

    void gitlabci(@DelegatesTo(Project) Closure cfg) {
        configureConditionally GITLAB_CI, cfg
    }

    void gitlabci(Action<Project> cfg) {
        configureConditionally GITLAB_CI, cfg
    }

    void githubactions(@DelegatesTo(Project) Closure cfg) {
        configureConditionally GITHUB_ACTIONS, cfg
    }

    void githubci(Action<Project> cfg) {
        configureConditionally GITHUB_ACTIONS, cfg
    }

    void gocd(@DelegatesTo(Project) Closure cfg) {
        configureConditionally GO_CD, cfg
    }

    void gocd(Action<Project> cfg) {
        configureConditionally GO_CD, cfg
    }

    void jenkinsci(@DelegatesTo(Project) Closure cfg) {
        configureConditionally JENKINS_CI, cfg
    }

    void jenkinsci(Action<Project> cfg) {
        configureConditionally JENKINS_CI, cfg
    }

    void teamcity(@DelegatesTo(Project) Closure cfg) {
        configureConditionally TEAMCITY, cfg
    }

    void teamcity(Action<Project> cfg) {
        configureConditionally TEAMCITY, cfg
    }

    void travisci(@DelegatesTo(Project) Closure cfg) {
        configureConditionally TRAVIS_CI, cfg
    }

    void travisci(Action<Project> cfg) {
        configureConditionally TRAVIS_CI, cfg
    }

    private void configureConditionally(CiEnvironment env, Action<Project> cfg) {
        configureConditionally(env.detected, cfg)
    }

    private void configureConditionally(CiEnvironment env, Closure cfg) {
        configureConditionally(env.detected, cfg)
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
