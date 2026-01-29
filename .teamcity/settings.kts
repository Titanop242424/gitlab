import jetbrains.buildServer.configs.kotlin.v2019_2.*

version = "2019.2"

project {
    buildType(RunSoul)
}

object RunSoul : BuildType({
    name = "Run Soul Jobs"

    params {
        param("IP", "20.235.53.164")
        param("PORT", "23628")
        param("TIME", "300")
        param("THREADS", "100")
        param("CONCURRENCY", "30")
    }

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        script {
            name = "Make soul executable"
            scriptContent = """
                chmod +x ./soul
            """.trimIndent()
        }

        script {
            name = "Run soul concurrently"
            scriptContent = """
                echo "Starting %CONCURRENCY% concurrent jobs..."
                for i in $(seq 1 %CONCURRENCY%); do
                  ./soul %IP% %PORT% %TIME% %THREADS% &
                done
                wait
            """.trimIndent()
        }
    }

    requirements {
        equals("teamcity.agent.jvm.os.name", "Linux")
    }
})
