#/bin/bash
for n in {1..400}; do
  a=`expr $n % 2` 
    texto="Impar"
    if [ $a -eq 0 ] ; then   
      texto="Par"
    fi
    wget --post-data "title=exemplo$n&content=conteudoExemplo$texto" http://localhost:8080/s/post
done; 
rm post*

