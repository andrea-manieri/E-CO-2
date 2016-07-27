#!/bin/bash
JAR_PATH=$HOME/workspace/E-CO-2/target/Traning-1.0-SNAPSHOT-jar-with-dependencies.jar
DICTIONARY_ALL=$HOME/workspace/E-CO-2/etc/dictionaryAll.csv 
STOPWORDS=$HOME/workspace/E-CO-2/etc/stopwords.csv
MODEL_PATH=$HOME/workspace/E-CO-2/etc/model
PROPS_FILE=$HOME/workspace/E-CO-2/etc/configure.properties

TRAIN_DOC_PATHS=()
CATEGORIES_FOLDER=$1
for i in $(ls -d $CATEGORIES_FOLDER/*)
do 
  TRAIN_DOC_PATHS+=($i)
done

for i in "${TRAIN_DOC_PATHS[@]}"
do
  for f in $i/*.txt
  do
    java -Dstop.words.file=$STOPWORDS -Ditemset.file=$DICTIONARY_ALL -Dmodel.path=$MODEL_PATH -jar $JAR_PATH -op x -i $i -o $i/terms.csv -p $PROPS_FILE
  done
done