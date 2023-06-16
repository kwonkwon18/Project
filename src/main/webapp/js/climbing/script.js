function fetchWeatherForecast() {
  fetch('/weather-forecast')
    .then(response => response.json())
    .then(data => {
      const resultDiv = document.getElementById('result');
      resultDiv.innerHTML = ''; // 이전 결과 삭제

      // 데이터 처리 및 결과 출력
      for (let i = 0; i < data.length; i++) {
        const item = data[i];
        const category = item.category;
        const obsrValue = item.obsrValue;

        const resultItem = document.createElement('p');
        resultItem.textContent = category + ': ' + obsrValue;
        resultDiv.appendChild(resultItem);
      }
    })
    .catch(error => {
      console.log('API 호출 에러:', error);
    });
}
