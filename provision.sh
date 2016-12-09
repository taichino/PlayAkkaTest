echo "deb https://dl.bintray.com/sbt/debian /" | sudo tee -a /etc/apt/sources.list.d/sbt.list
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 642AC823
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update

# scala/play
echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
sudo apt-get -y install oracle-java8-installer
sudo apt-get -y install scala
sudo apt-get -y install sbt

# nodejs
sudo apt-get -y install nodejs
sudo apt-get -y install npm

# util
sudo apt-get -y install tree
