pipeline {
	agent any
	
	tools {
		maven 'maven-3.5.2'
	}
	
	stages {
		stage("build") {
			steps {
				sh 'mvn clean compile'
			}
		}
	}
}
