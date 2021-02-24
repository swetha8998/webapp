  
node{
    stage("checkout"){
        checkout scm
    }
    stage("Build"){
       
     sh "mvn clean compile package"
    }
   
    stage("deploy"){
      timeout(time: 15, unit: "MINUTES") {    input message: 'Do you want to approve the deploy in production?', ok: 'Yes'}
   sshagent(['3e61a85b-994b-47e6-9c94-78e83f54ba82']) {
    cp -p /var/lib/jenkins/workspace/webapp/target/simplewebapp.war /opt/tomcat/webapps
     
   }}

    stage("backup"){
      sh "mkdir /opt/backup"
      sh '''if [ -d "/opt/backup" ]
        then
          echo "backup already exist"
      else 
        cp -p /var/lib/jenkins/workspace/webapp/target/simplewebapp.war /opt/backup"
      fi
       echo \'backup is created\' '''
       
    }


}

