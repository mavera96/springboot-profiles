En este repo se documenta el uso de los profiles de Springboot.

# Properties
En la ruta `src/main/resources`se encuentran tres archivos .properties
1. application.properties
2. application-local.properties
3. application-cloud.properties

Que se resume en la existencia de dos *profiles*: *local* y *cloud*. El archivo `application.properties`se utiliza como el *properties* con parámetros generales y en este ejemplo, en él se configura el *profile* que se usará por *default* al ejecutar el .jar, que en este caso es el *local*

En `application-local.properties` encontramos el parámetro que utilizaremos declarado de manera explicita, mientras que en `application-cloud.properties` el valor se obtiene desde una variable de entorno que se encuentre en configurada en el recurso de cómputo (*Cloud Run* o *GKE*)

# Implementación
En el archivo `src/main/java/com/example/springboot/HelloController.java` se muestra el uso del *annotation* `@Value` para inyectar un parámetro desde los *properties*, mismo que irá cambiando su valor dependiendo del *profile* de *Springboot* indicado.
> [!NOTE]
> El *annotation* `@Value` es una de las varias opciones en la que podemos inyectar los parámetros. Aquí solo se muestra esta opción para ejemplificar el uso de los *profiles*

```
package com.example.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@Value("${liverpool.message}")
	private String message;

	@GetMapping("/")
	public String index() {
		return message;
	}
}
```

Como se muestra en el bloque de código anterior:
1. Necesitamos importar el annotation `@Value` <br>
```
import org.springframework.beans.factory.annotation.Value;
```
2. Se inyecta el parámetro que se encuentra en nuestros archivos properties<br>
```
	@Value("${liverpool.message}")
	private String message;
```
# Prueba
1. Genera el jar: `mvn -ntp -U clean package`
## Perfil local
1. Ejecuta el jar: `java -jar target/*.jar`
> [!NOTE]
> Esto ejecuta el aplicativo sin indicar un *profile*, por lo que utilizará el que se configuró de manera *default*.
3. Hacer un llamado y verificar que es el valor indicado en el *profile* *local*: `curl localhost:8080`
## Perfil cloud
1. Genera la variable de ambiente definida en el *profile* *cloud*: `export HELLO_MESSAGE="Hello from cloud"`
2. Ejecuta el jar indicando el *profile* *cloud*: `java -jar -Dspring.profiles.active=cloud target/spring-boot-initial-0.0.1-SNAPSHOT.jar`
3. Hacer un llamado y verificar que es el valor indicado en el *profile* *cloud*: `curl localhost:8080`
