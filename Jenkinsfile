pipeline {
	agent any
	
	stages {
		stage("build") {
			steps {
				Maven('maven') {
					sh 'mvn clean compile'
				}
			}
		}
	}
}
