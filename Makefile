JAVAC = javac
JAVA = java
JAVA_FILES=$(wildcard *.java)
BIN_DIR = bin
SERVER_CLASS = StoreServer
CLIENT_CLASS = StoreClient
RMI_PORT = 1099
JAR_FILE = MyApplication.jar

compile:
	mkdir -p $(BIN_DIR)
	$(JAVAC) -d $(BIN_DIR) $(JAVA_FILES)

jar: compile
	jar cvfe $(JAR_FILE) $(SERVER_CLASS) -C $(BIN_DIR) .

run-server:
	$(JAVA) -cp $(BIN_DIR) $(SERVER_CLASS)

run-client:
	$(JAVA) -cp $(BIN_DIR) $(CLIENT_CLASS)

clean:
	rm -rf $(BIN_DIR)/*

start-rmi:
	rmiregistry $(RMI_PORT)


