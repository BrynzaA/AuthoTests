FROM aerokube/selenoid:latest

COPY browsers.json /etc/selenoid/browsers.json

EXPOSE 4444

CMD ["/usr/bin/selenoid", "-conf", "/etc/selenoid/browsers.json", "-video-output-dir", "/opt/selenoid/video", "-log-output-dir", "/opt/selenoid/logs", "-container-network", "test-network"]