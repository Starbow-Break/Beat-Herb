from kafka import KafkaConsumer
import json
import subprocess
# Kafka 소비자 설정
import os

KAFKA_BOOTSTRAP_SERVER = os.environ.get("KAFKA_BOOTSTRAP_SERVER")
RESOURCE_DIRECTORY = os.environ.get("RESOURCE_DIRECTORY")
MUSIC_REFERENCE_DIRECTORY = RESOURCE_DIRECTORY + "/music/reference"
MUSIC_CROPPED_DIRECTORY = RESOURCE_DIRECTORY + "/music/cropped"
MUSIC_CONVERT_DIRECTORY = RESOURCE_DIRECTORY + "/music/convert"

bootstrap_servers = KAFKA_BOOTSTRAP_SERVER  # Kafka 브로커 서버 목록
topic = 'hls'  # 소비할 토픽

# Consumer 설정
consumer = KafkaConsumer(
    topic,
    group_id='hls',  # Consumer 그룹 ID
    bootstrap_servers=bootstrap_servers,
    auto_offset_reset='earliest',  # 소비자 그룹이 처음으로 오프셋을 설정할 때 어떻게 처리할지 설정
)

try:
    for message in consumer:
        # 메시지의 key와 value 출력
        key = message.key.decode('utf-8') if message.key else None
        value = message.value.decode('utf-8') if message.value else None
        print(value)

        request = json.loads(value)
        REFERENCE_FILE_DIRECTORY = f"{MUSIC_REFERENCE_DIRECTORY}/{request['fileName']}"

        fileNumber = request['fileName'].split(".")[0]
        CROPPED_FILE_DIRECTORY = f"{MUSIC_CROPPED_DIRECTORY}/{fileNumber}"
        CONVERT_FILE_DIRECTORY = f"{MUSIC_CONVERT_DIRECTORY}/{fileNumber}.mp3"
        if not os.path.exists(CROPPED_FILE_DIRECTORY):
            os.makedirs(CROPPED_FILE_DIRECTORY)

        print("==processing==")
        cmd = f"ffmpeg -i {REFERENCE_FILE_DIRECTORY} -c:a aac -b:a 192k -hls_time 10 -hls_list_size 0 -hls_segment_filename {CROPPED_FILE_DIRECTORY}/%03d.aac -hls_base_url {fileNumber}/ {CROPPED_FILE_DIRECTORY}/playlist.m3u8"
        result = subprocess.Popen(
            cmd.split(" "), stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        out, err = result.communicate()
        exitcode = result.returncode

        cmd = f"ffmpeg -i {REFERENCE_FILE_DIRECTORY} {CONVERT_FILE_DIRECTORY}"
        result = subprocess.Popen(
            cmd.split(" "), stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        out, err = result.communicate()
        exitcode = result.returncode


    
        print("==end-processing==")

except KeyboardInterrupt:
    pass

finally:
    # Consumer 종료
    consumer.close()
