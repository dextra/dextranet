#/bin/bash
for n in {1..400}; do
  a=`expr $n % 3` 
    texto="Normal"
    if [ $a -eq 0 ] ; then   
      texto="Divisivel3"
    fi
    wget --post-data "title=exemplo$n&content=conteudoExemplo$texto" http://localhost:8080/s/post
done; 
rm post*

