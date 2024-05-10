Web socket demo project.
In order to generate the SSL certificate, use the below command,
keytool -genkey -keyalg RSA -alias selfsigned -keystore keystore.jks -storepass password -validity 360 -keysize 2048 -storetype pkcs12 -dname "CN=localhost, OU=ID, O=Dummy, L=Dummy, S=Dummy, C=Dummy"
