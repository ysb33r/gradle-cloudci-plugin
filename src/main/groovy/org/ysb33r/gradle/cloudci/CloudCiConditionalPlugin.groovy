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
