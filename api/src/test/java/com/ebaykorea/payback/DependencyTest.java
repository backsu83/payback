package com.ebaykorea.payback;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class DependencyTest {

  @Test
  @DisplayName("core 패키지는 util 패키지를 제외한 다른 패키지를 참조 할 수 없다")
  void coreDependencyTest() {
    final var importedClasses = new ClassFileImporter().importPackages("com.ebaykorea.payback");

    final var rule = layeredArchitecture()
        .consideringOnlyDependenciesInAnyPackage("com.ebaykorea.payback")
        .layer("Api").definedBy("..api..")
        .layer("Core").definedBy("..core..")
        .layer("Infrastructure").definedBy("..infrastructure..")

        .whereLayer("Api").mayNotBeAccessedByAnyLayer()
        .whereLayer("Core").mayOnlyBeAccessedByLayers("Api", "Infrastructure")
        .whereLayer("Infrastructure").mayOnlyBeAccessedByLayers("Api");

    rule.check(importedClasses);
  }
}
