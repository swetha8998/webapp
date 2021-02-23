  
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
  sh "whoami"
     sh '''if [ $(ps -ef | grep tomcat | wc -l) == 1 ]
 then 
   
   cp -p /var/lib/jenkins/workspace/webapp/target/simplewebapp.war /opt/tomcat/webapps
   cd /opt/tomcat/bin
   echo "inside if"
   ./startup.sh
   ps -ef |grep tomcat
   
   
else
   cd /opt/tomcat/bin
   echo "inisde else"
   ./shutdown.sh
   cp -p /var/lib/jenkins/workspace/webapp/target/simplewebapp.war /opt/tomcat/webapps
    ./startup.sh
    ps -ef |grep tomcat
    curl http://192.168.56.23:8090/simplewebapp/
    
fi'''
     
   
      sh "mkdir /opt/backup"
      sh "cp -p /var/lib/jenkins/workspace/webapp/target/simplewebapp.war /opt/backup"
      sh " echo 'backup is created' "
      
      sh "ps -ef | grep tomcat"
    

  
   }
    }

}
    

