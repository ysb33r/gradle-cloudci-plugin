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
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 */
@CompileStatic
class CiConditionalPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.create('cloudci',CloudCiExtension,project)
        project.extensions.create('ci',CiExtension,project)
    }
}
