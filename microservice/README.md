# springDataRest

Ce projet rassemble plusieurs informations pratique pour la mise en place d'un micro-service.

## Spring data Rest

Pour faciliter le développement, nous faisons usage de spring data rest qui permet la création automatique des opérations CRUD d'une entité.

    <dependency>  
     <groupId>org.springdoc</groupId>  
     <artifactId>springdoc-openapi-data-rest</artifactId>  
     <version>1.6.6</version>  
    </dependency>

Avec cette dépendance maven, il est désormais possible de générer les méthodes crud automatiquement à l'aide de l'annotation **@RepositoryRestResource** dans le repository correspondant.

Il n'y a aucune opération supplémentaire à réaliser.

Si on ajoute une méthode personnalisée, il est possible de l'annotée avec **@RestResource(path = "/mypath")** cela permettra de l'ajouter à la liste des ressources.

## Pagination et Projection
Lorsque l'on travaille avec beaucoup de données il peut être intéressant de réduire le nombre de résultats souhaités. Pour cela il y a deux techniques, la pagination et la projection.

### La pagination

Elle permet de filtrer le nombre de résultat souhaiter dans des pages. On indique alors la page que l'on souhaite afficher d'un côté, et le nombre d'éléments par pages. Pour ce faire il faut simplement s'assurer que le repository étende la classe "JpaRepository". Celle-ci incluant de base ces fonctionnalités.

La syntaxe pour faire appel à ces options est la suivante:

> http://mapage:monport/context/nomentité/endpoint?page=x&size=y
**page** correspond à la page que l'on souhaite afficher. (si nous avons 400 éléments et 100 éléments par page nosu avons donc 4 pages)
**size** correspond au nombre d'éléments par page souhaités, par défaut nous en récupérons 20.

### La projection

Cette technique permet de réduire les champs que nous souhaitons récupérés. Par exemple si nous ne souhaitons que récupérer le nom d'une personne lors d'une requête on peut créer une projection qui correspond à ce besoin. La projection se définit ainsi pour une classe "Account" ayant pour paramètre :

- code : Long
- balance: double
- createdAt: Date
- accountType: AccountType(Enum représentant les différents comptes)




>   @Projection(name = "p1",types = Account.class)  
>     public interface AccountProjection1 {  
>         Long getCode();  
>      double getBalance();  
>     }
On peut voir l'ajout d'une annotation **@Projection** avec les paramètres **name** et **types**. Le premier permet notammetn de nommer la projection et le deuxième d'applique la projection à une classe en particulier.

On retrouve ensuite dans la définition de l'interface deux méthodes, **getCode** et **getBalance** qui permette donc de récupérer simplement l'attribut **code** et l'attribut **balance** de l'entité.

## Swagger
Swagger permet d'extraire les informations  liés aux requêtes REST de l'application. Pour sa mise en place on ajoute deux dépendances au pom qui sont les suivantes:

     <dependency>  
     <groupId>org.springdoc</groupId>  
     <artifactId>springdoc-openapi-ui</artifactId>  
     <version>1.6.6</version>  
    </dependency>  
    <dependency>  
     <groupId>org.springdoc</groupId>  
     <artifactId>springdoc-openapi-data-rest</artifactId>  
     <version>1.6.6</version>  
    </dependency>
La première dépendance **springdoc-openapi-ui** permet d'obtenir toutes les requêtes de notre application au format json en se rendant sur l'url suivant:
> http://mapage:monport/context/v3/api-docs
ou de se rendre sur une interface web réunissant toutes les requêtes et les rendant executable à l'adresse url suivante :
> http://mapage:monport/context/swagger-ui.html
La seconde dépendance **springdoc-openapi-data-rest** permet  d'intégrer les requêtes générées automatiquement par Spring Data Rest.

## Actuator

Actuator est un outil permettant d'obtenir des données sur notre application. Pour l'implémenter il suffit simplement d'ajouter la dépendance suivante:

    <dependency>  
     <groupId>org.springframework.boot</groupId>  
     <artifactId>spring-boot-starter-actuator</artifactId>  
    </dependency>

et en se rendant sur l'url suivante :

> http://mapage:monport/context/actuator
nous avons accès aux informations de actuator. Il existe ensuite différents endpoint pour différentes informations :




| Endpoint| Usage|
|--|--|
| /auditevents      | Returns all auto-configuration candidates and the reason why they ‘were’ or ‘were not’ applied.                                                              |
| /beans            | Returns a complete list of all the Spring beans in your application.                                                                                         |
| /mappings         | Displays a collated list of all @RequestMapping paths..                                                                                                      |
| /env              | Returns list of properties in current environment                                                                                                            |
| /health           | Returns application health information.                                                                                                                      |
| /caches           | It exposes available caches.                                                                                                                                 |
| /conditions       | Shows the conditions that were evaluated on configuration and auto-configuration.                                                                            |
| /configprops      | It displays a collated list of all @ConfigurationProperties.                                                                                                 |
| /integrationgraph | It shows the Spring Integration graph. Requires a dependency on spring-integration-core.                                                                     |
| /loggers          | The configuration of loggers in the application..                                                                                                            |
| /scheduledtasks   | Displays the scheduled tasks in the application.                                                                                                             |
| /sessions         | Returns trace logs (by default the last 100 HTTP requests). Requires an HttpTraceRepository bean.                                                            |
| /httptrace        | It allows retrieval and deletion of user sessions from a Spring Session-backed session store. Requires a Servlet-based web application using Spring Session. |
| /shutdown         | Lets the application be gracefully shutdown. Disabled by default.                                                                                            |
| /threaddump       | It performs a thread dump.                                                                                                                                   |
| /metrics          | It shows several useful metrics information like JVM memory used, system CPU usage, open files, and much more.    
