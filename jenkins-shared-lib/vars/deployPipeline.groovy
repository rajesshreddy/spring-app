def call(Map config) {

    pipeline {
        agent any

        parameters {
            choice(name: 'ENV', choices: ['dev', 'prod'], description: 'Select Environment')
        }

        stages {

            stage('Checkout') {
                steps {
                    git config.repo
                }
            }

            stage('Build & Deploy') {
                steps {
                    script {
                        sh """
                        cd ansible
                        ansible-playbook -i inventory.ini site.yml \
                        -e target_env=${params.ENV}
                        """
                    }
                }
            }
        }
    }
}