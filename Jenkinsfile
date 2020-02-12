pipeline {

//    https://medium.com/@gustavo.guss/jenkins-building-docker-image-and-sending-to-registry-64b84ea45ee9

    //    https://github.com/jenkinsci/pipeline-examples/blob/master/pipeline-examples/gitcommit/gitcommit.groovy


    agent any


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
//        stage('lint') {
//            steps {
//                sh './gradlew spotlessCheck'
//            }
//        }
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
//                    NEED TO INSTALL  HTLMPublisher Jenkins plugin first
//                    https://stackoverflow.com/questions/50649363/no-such-dsl-method-publishhtml-found-among-steps
                    publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true,
                                 reportDir   : "build/reports/tests/test", reportFiles: 'index.html',
                                 reportName  : "Unit Tests Report", reportTitles: ''])
                }
            }
        }
        stage('integration tests') {
            steps {
                sh './gradlew integrationTest'
            }
            post {
                always {
                    publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true,
                                 reportDir   : "build/reports/tests/integrationTest", reportFiles: 'index.html',
                                 reportName  : "Integration Tests Report", reportTitles: ''])
                }
            }
        }

//        https://jenkins.io/doc/book/pipeline/docker/#custom-registry
        stage('docker') {
            environment {
                REGISTRY = "ilja07/country-phone"
                DOCKER_IMAGE = 'country-phone'
                DOCKER_TAG = sh(returnStdout: true, script: "git rev-parse --short=8 HEAD").trim()
            }
            when {
                branch 'master'
            }
            steps {
                script {
                    def image = docker.build("${REGISTRY}", '-f Dockerfile .')
//                    image.push("${DOCKER_TAG}")
//                    image.push()

                    //docker-hub-credentials needs to be added manually via Jenkins UI -> Credentials -> Jenkins global
                    docker.withRegistry('https://registry-1.docker.io/v2/', 'docker-hub-credentials') {
                        image.push("${DOCKER_TAG}")
//                      image without tag - is LATEST by default
                        image.push()
                    }
                }
            }
        }

//    post {
//        always {
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
//        }
//    }


    }

    //?FUNCTIONS? - https://stackoverflow.com/questions/47628248/how-to-create-methods-in-jenkins-declarative-pipeline/47631522
//    def showMavenVersion(String a) {
//        bat 'mvn -v'
//        echo a
//    }
}