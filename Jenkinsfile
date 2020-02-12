pipeline {

//    https://medium.com/@gustavo.guss/jenkins-building-docker-image-and-sending-to-registry-64b84ea45ee9

    //    https://github.com/jenkinsci/pipeline-examples/blob/master/pipeline-examples/gitcommit/gitcommit.groovy





//    agent {
//        kubernetes {
//            cloud 'leon-build-cluster-dev'
//            label "${JOB_BASE_NAME}-${BUILD_NUMBER}"
//            yamlFile 'devops/pipeline/agent.yaml'
//        }
//    }
//    options {
//        disableConcurrentBuilds()
//        gitLabConnection('gitlab.leoncorp.net')
//    }
//    environment {
//        SLACK_CHANNEL = '#traffic-panel-internal'
//        SLACK_TOKEN = 'n428BZEvAjBNRjk5DN5cvvv8'
//    }
    stages {
        stage('init') {
            steps {
                sh 'whoami && pwd && ls -la && printenv | sort'

//                script {
//                    if (isMergeRequest()) {
//                        updateGitlabCommitStatus state: 'running'
//                    }
//                }
            }
        }
        stage('lint') {
            steps {
                sh './gradlew spotlessCheck'
            }
        }
        stage('build') {
            steps {
                sh './gradlew assemble'
            }
        }
        stage('test') {
            steps {
                sh './gradlew test'
            }
            post {
                always {
                    publishTestReport('Unit Tests Report', 'build/reports/tests/test')
                }
            }
        }
        stage('integration tests') {
            steps {
                sh './gradlew integrationTest'
            }
            post {
                always {
                    publishTestReport('Integration Tests Report', 'build/reports/tests/integrationTest')
                }
            }
        }

        stage('docker') {
            environment {
                registry = "ilja07/country-phone"
                registryCredential = 'dockerhub'
                DOCKER_IMAGE = 'country-phone'
                DOCKER_TAG = sh(returnStdout: true, script: "git rev-parse --short=8 HEAD").trim()
            }
            when {
                branch 'master'
            }
            steps {
                script {
                    def image = docker.build("${DOCKER_IMAGE}", '-f Dockerfile .')
                    image.push("${DOCKER_TAG}")
                    image.push()
                }
            }
        }
    }
    post {
        always {
//            notifySlack(getCommitMsg(), currentBuild.currentResult)
//        }
//        aborted {
//            script {
//                if (isMergeRequest()) {
//                    updateGitlabCommitStatus state: 'canceled'
//                }
//            }
//        }
//        failure {
//            script {
//                if (isMergeRequest()) {
//                    updateGitlabCommitStatus state: 'failed'
//                }
//            }
//        }
//        success {
//            script {
//                if (isMergeRequest()) {
//                    updateGitlabCommitStatus state: 'success'
//                }
//            }
        }
    }
}
