pipeline {
	agent any
	
	stages {
		stage("build") {
			steps {
				maven('maven') {
					sh 'mvn clean compile'
				}
			}
		}
	}
}
