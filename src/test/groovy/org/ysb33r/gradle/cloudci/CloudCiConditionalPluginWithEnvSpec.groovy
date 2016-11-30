package org.ysb33r.gradle.cloudci

import org.gradle.testfixtures.ProjectBuilder
import spock.lang.IgnoreIf
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Unroll


class CloudCiConditionalPluginWithEnvSpec extends Specification {

    @Unroll
    def 'Testing #name run with environment'() {
        assert System.getenv()[envVar]

        when:
        def project = ProjectBuilder.builder().build()
        project.apply plugin : 'org.ysb33r.cloudci'
        project."${extObj}" {
            ext {
                foo = 'bar'
            }
        }
        project.evaluate()

        then:
        project.ext.foo == 'bar'

        where:
        name        | envVar     | extObj
        'appveyor'  | 'APPVEYOR' | 'appveyor'
        'travis-ci' | 'TRAVIS'   | 'travisci'
        'circle-ci' | 'CIRCLECI' | 'circleci'
    }

}