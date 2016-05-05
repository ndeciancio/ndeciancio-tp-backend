Change-log:

	- Se modifico la estructura de archivos para que corresponda con la estructura de un proyecto maven, ahora la carpeta rais es la carpeta misma del proyecto

	- Los metodos que tenian nombres genericos de tipo "generate()" en las clases AirportService, CityService y CurrencyService ahora se llaman "generateAirport()", "generateCity()" y "generateCurrency()" respectivamente. El metodo "getParent()" de la clase Currency ahora se llama "getCountry()" apropiadamente.

	- Revisando el enunciado no encontre ningun ejemplo referente al bonus ni alguna especificacion sobre que nombre debe llevar el parametro

	- Se agrego un test donde se prueba que poniendo el lenguaje en minuscula devuelve las descripciones apropiadamente

	- Agregado archivo logback.xml

	- Reparados los tests de integracion que antes daban error

	- Referente al test que captura una excepcion, se agregaron dos tests en AirportServiceTest que contemplan este tipo de casos

	- Ahora los ErrorResponse de la aplicacion son mas descriptivos. Ejemplo ahora si se pasa un codigo iata incorrecto la aplicacion devuelve el mensaje "Bad Request. Invalid request for IATA code xxx" junto con su respectivo codigo de error 400

	- Agregado @Ignore a la clase BackEndIntegrationTest (duda sobre esto, si se quiere correrlo con junit es ignorado, hay que borrarlo dicho caso)

	Cambios adicionales:
		
		- Se agregaron las interfaces AirportServiceInterface, CityServiceInterface y CurrencyServiceInterface para agregar robustes al diseño

		- Ya no se lanzan excepciones genericas del tipo RuntimeException, en su lugar se agregaron excepciones propias que encapsulan estas excepciones aunque siguen siendo del tipo RuntimeException

		- Las excepciones propias ahora son inmutables
