package com.serwa.boilerplate

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.ArchRule
import spock.lang.Specification

abstract class BaseArchUnitSpec extends Specification {

	private JavaClasses allClasses = new ClassFileImporter()
		.withImportOption(new ImportOption.DoNotIncludeTests())
		.importPackages("com.serwa")

	protected void check(ArchRule rule) {
		rule.check(allClasses)
	}
}
