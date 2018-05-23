package com.joker.test

import com.android.build.gradle.api.ApkVariantOutput
import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.tasks.PackageApplication
import org.gradle.api.Plugin
import org.gradle.api.Project

class HookAssetsPlugin implements Plugin<Project> {
  @Override
  void apply(Project project) {
    project.afterEvaluate {
      project.plugins.withId('com.android.application') {
        project.android.applicationVariants.all { ApplicationVariant variant ->
          println variant.toString()
          variant.outputs.each { ApkVariantOutput variantOutput ->
            println variantOutput.toString()
            if (variantOutput.name.equalsIgnoreCase("release")) {
              variantOutput.packageApplication.doFirst { PackageApplication task ->
                project.copy {
                  from "${project.projectDir.absolutePath}/pic/test.png"
                  into "${task.assets.asPath}"
                }
              }
            }
          }
        }
      }
    }

    //    project.afterEvaluate {
    //      project.plugins.withId('com.android.application') {
    //        project.android.applicationVariants.all { ApplicationVariant variant ->
    //          variant.outputs.each { ApkVariantOutput variantOutput ->
    //            if (variant.name.equalsIgnoreCase("release")) {
    //              project.tasks.findByName("package${variant.name.capitalize()}").
    //                  doFirst { PackageApplication task ->
    //                    def hookTask = project.tasks.create(
    //                        name: "hookAssetsFor${variant.name.capitalize()}", type: Copy) {
    //                      from "${project.projectDir.absolutePath}/pic/test.png"
    //                      into "${task.assets.asPath}"
    //                    }
    //                    hookTask.execute()
    //                  }
    //            }
    //          }
    //        }
    //      }
    //    }
  }
}
