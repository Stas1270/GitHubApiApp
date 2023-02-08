package com.stas1270.githubapi.data.di

/**
 * Annotation for kotlin-allopen plugin (make all class open for mock it).
 *
 * Remember to add in build.gradle:
 *
 * buildscript {
 *  repositories {
 *      jcenter()
 *  }
 *  dependencies {
 *      classpath "org.jetbrains.kotlin:kotlin-allopen:$kotlin_version"
 *  }
 * }
 *
 * apply plugin: 'kotlin-allopen'
 *
 * allOpen {
 *  annotation 'com.stas1270.githubapi.data.di.OpenClass'
 * }
 */
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class OpenClass
