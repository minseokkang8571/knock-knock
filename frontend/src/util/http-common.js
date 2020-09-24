import axios from 'axios'

// axios 객체 생성
export default axios.create({
  baseURL: 'http://127.0.0.1:4000',
  headers: {
    'Content-type': 'application/json'
  }
})
