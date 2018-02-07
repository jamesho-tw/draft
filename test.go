package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
	"strings"
	"time"

	"github.com/tarm/serial"
)

type Metadata struct {
	Id         string `json:"id"`
	CardNo     string `json:"card_no"`
	AccessTime int64  `json:"timestamp"`
}

type Data struct {
	Message string `json:"message"`
}

func getTimestamp() int64 {
	return time.Now().UnixNano() / int64(time.Millisecond)
}

func httpPost(str string) {
	pin := strings.Replace(str, "\r\n", "", -1)
	timestamp := getTimestamp()
	raw := Metadata{"pn532001", pin, timestamp}
	b, _ := json.Marshal(raw)
	//buf := `{"pin":"` + pin + `","timestamp":` + strconv.FormatInt(getTimestamp(), 10) + `}`
	url := "http://localhost:8080/test"
	req, err := http.NewRequest("POST", url, bytes.NewBuffer(b))
	req.Header.Set("Content-Type", "application/json")
	client := &http.Client{}
	resp, err := client.Do(req)
	if err != nil {
		panic(err)
	}
	defer resp.Body.Close()
	fmt.Println("response Status:", resp.Status)
	//fmt.Println("response Headers:", resp.Header)
	body, _ := ioutil.ReadAll(resp.Body)
	var data Data
	json.Unmarshal(body, &data)
	if data.Message == "OK" {
		log.Printf("%s >>> %s", b, data.Message)
	} else {
		log.Printf("%s", b)
	}
}

func main() {
	c := &serial.Config{Name: "COM8", Baud: 115200, ReadTimeout: time.Millisecond * 500}
	s, err := serial.OpenPort(c)
	if err != nil {
		log.Fatal(err)
	}
	buf := make([]byte, 50)
	for {
		n, err := s.Read(buf)
		if err != nil {
			continue
		}
		if n > 0 {
			httpPost(string(buf[:n]))
		}
	}
}
