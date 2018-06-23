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
package org.ysb33r.gradle.cloudci.internal

import groovy.transform.CompileStatic
import org.gradle.api.tasks.testing.TestResult.ResultType

/**
 * @since 2.3
 */
@CompileStatic
class AppveyorUtils {

    static String mapTestResult(ResultType result) {
        switch(result) {
            case ResultType.FAILURE:
                'Failed'
                break
            case ResultType.SUCCESS:
                'Passed'
                break
            case ResultType.SKIPPED:
                'Skipped'
                break
        }
    }
}
