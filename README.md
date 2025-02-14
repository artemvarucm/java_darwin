<h2>JAVA DARWIN</h2>
<h3>SOBREVIVEN LOS MÁS FUERTES</h3>

<h3>Tabla de contenidos</h3>

- <a href="#Eclipse-Setup">Setup del entorno en Eclipse</a>

- <a href="#Creación-del-ejecutable">Creación del ejecutable</a>

### Eclipse Setup
Setup para que el proyecto funcione correctamente en Eclipse
Entramos en configuración de Java Build Path

<img src="readme-images%2Fbuild-path.png" width="50%"/>

Añadimos las siguientes carpetas a traves de "Add folder"

<img src="readme-images%2Fsources.png" width="60%"/>

Entrar a Run Configurations y seleccionar Java Application -> New Configuration

<img src="readme-images%2Frun-java.png" width="50%"/>

#### Tests (opcional)
La librería usada para los tests es JUnit

<img src="readme-images%2Fadd-libr.png" height="300"/><img src="readme-images%2Fjunit.png" height="300"/>

Entrar a Run Configurations y seleccionar JUnit -> New Configuration

Escoger la opcion "Run all tests..." y seleccionar el proyecto de Camel Up

<img src="readme-images%2Ftest-config.png" width="45%"/>

### Creación del ejecutable
Antes de empezar, tenemos que generar el archivo .jar (una especie de zip que recoge codigo java)

En Eclipse Abrir la ventana Export

<img src="readme-images%2Fjar-select.png" height="400"/><img src="readme-images%2Fjar-export.png" height="400"/>

#### Windows
Instalar Wix (para crear installer)
https://wixtoolset.org/docs/wix3/
1. Crear un directorio (ej. camel)
2. Dentro crear una carpeta _build_, a la cual se añade el .jar creado anteriormente
3. Copiar app_icon.ico a build
4. Estando en camel, guardamos los archivos necesarios del entorno java

`jlink --output minimal-jre --add-modules java.desktop,jdk.unsupported`
5. Estando en camel, creamos el ejecutable

`jpackage -t exe -n camelup --main-jar project-camelup_gp.jar -d out -i build --icon ./build/app_icon.ico --app-version 1.0 --runtime-image minimal-jre --win-shortcut --win-per-user-install`

#### MacOS
1. Crear un directorio (ej. camel)
2. Dentro crear una carpeta _build_, a la cual se añade el .jar creado anteriormente
3. Copiar camelup-icon.icns a build
4. Estando en camel, guardamos los archivos necesarios del entorno java

`jlink --output minimal-jre --add-modules java.desktop,jdk.unsupported`
5. Estando en camel, creamos el ejecutable

`jpackage -t dmg -n camelup --main-jar project-camelup_gp.jar -d ./out -i ./build --icon ./build/camelup-icon.icns --app-version 1.0 --runtime-image ./minimal-jre`