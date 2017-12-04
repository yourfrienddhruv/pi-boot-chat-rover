cd src/main/resources

keytool -genkey -keyalg RSA -alias selfsigned -keystore keystore.jks -storepass password -validity 360 -keysize 2048

keytool -importkeystore -srckeystore keystore.jks -destkeystore keystore.pkcs12 -deststoretype pkcs12