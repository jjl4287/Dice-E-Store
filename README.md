# E-Store:  DND Store selling different types of dice

An online E-store system built in Java 8=>11 and Angular
  
## Team

- Vincent Sarubbi
- Maximo Bustillo
- Chris Ferioli
- Jakob Langtry
- Brady Self


## Prerequisites

- Java 8=>11 (Make sure to have correct JAVA_HOME setup in your environment)
- Maven
- Node/NPM
- Angular 14.2.6


## How to run it

1. Clone the repository and go to the root directory.
2. Execute `mvn compile exec:java`
3. Move to the estore-ui directory.
4. Execute `npm install`
5. Execute `ng serve`
6. Open in your browser `http://localhost:4200/`

## Known bugs and disclaimers

This implementation would not work (mostly) if it were deployed publically, and multiple
people were to log in at the same time. There is only one guest user, and logged in user
only has one instance and is stored in the backend.

## How to test it

The Maven build script provides hooks for run unit tests and generate code coverage
reports in HTML.

To run tests on all tiers together do this:

1. Execute `mvn clean test jacoco:report`
2. Open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/index.html`

To run tests on a single tier do this:

1. Execute `mvn clean test-compile surefire:test@tier jacoco:report@tier` where `tier` is one of `controller`, `model`, `persistence`
2. Open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/{controller, model, persistence}/index.html`

To run tests on all the tiers in isolation do this:

1. Execute `mvn exec:exec@tests-and-coverage`
2. To view the Controller tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`
3. To view the Model tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`
4. To view the Persistence tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`

*(Consider using `mvn clean verify` to attest you have reached the target threshold for coverage)
  
  
## How to generate the Design documentation PDF

1. Access the `PROJECT_DOCS_HOME/` directory
2. Execute `mvn exec:exec@docs`
3. The generated PDF will be in `PROJECT_DOCS_HOME/` directory

## License

MIT License

See LICENSE for details.
