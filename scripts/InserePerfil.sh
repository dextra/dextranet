#/bin/bash
wget --post-data 'name=campinas' http://localhost:8080/s/unidade/inserir
wget --post-data 'name=campo grande' http://localhost:8080/s/unidade/inserir
wget --post-data 'name=rh' http://localhost:8080/s/area/inserir
wget --post-data 'name=adm' http://localhost:8080/s/area/inserir
wget --post-data 'name=teste&nickName=Teds&area=desenvolvimento&unit=Cg&branch=21&skype=Teds&gTalk=luizGmail&phoneResidence=(18)3281-3303&phoneMobile=(67)8130-5889' http://localhost:8080/s/perfil/inserir
