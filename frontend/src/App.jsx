import { Routes, Route, Navigate } from 'react-router-dom'
import Home from './pages/Home'
import UserQuestions from './pages/UserQuestions'

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/users/:userId" element={<UserQuestions />} />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  )
}
