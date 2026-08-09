import { useEffect, useMemo, useRef, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import api from '../api'

const statuses = [
  { value: 'NOT_STARTED', label: 'Not Started' },
  { value: 'DONE', label: 'Done' },
  { value: 'REVISED', label: 'Revised' },
  { value: 'MOCK_INTERVIEW', label: 'Asked in Mock' },
]

function statusLabel(status) {
  return statuses.find((s) => s.value === status)?.label || status
}

export default function UserQuestions() {
  const { userId } = useParams()
  const [questions, setQuestions] = useState([])
  const [text, setText] = useState('')
  const [filter, setFilter] = useState('ALL')
  const [error, setError] = useState('')
  const fileRef = useRef(null)

  const loadQuestions = async () => {
    try {
      const response = await api.get(`/users/${userId}/questions`)
      setQuestions(response.data)
      setError('')
    } catch {
      setError('Could not load questions.')
    }
  }

  useEffect(() => {
    loadQuestions()
  }, [userId])

  const addQuestion = async (event) => {
    event.preventDefault()
    if (!text.trim()) return

    try {
      await api.post(`/users/${userId}/questions`, {
        questionText: text.trim(),
        status: 'NOT_STARTED',
      })
      setText('')
      await loadQuestions()
    } catch (e) {
      setError(e.response?.data?.message || 'Could not add question.')
    }
  }

  const updateStatus = async (question, status) => {
    try {
      await api.put(`/questions/${question.id}`, {
        questionText: question.questionText,
        status,
      })
      setQuestions((current) =>
        current.map((q) => q.id === question.id ? { ...q, status } : q)
      )
    } catch {
      setError('Could not update status.')
    }
  }

  const deleteQuestion = async (id) => {
    try {
      await api.delete(`/questions/${id}`)
      setQuestions((current) => current.filter((q) => q.id !== id))
    } catch {
      setError('Could not delete question.')
    }
  }

  const exportExcel = async () => {
    try {
      const response = await api.get(`/users/${userId}/excel/export`, {
        responseType: 'blob',
      })
      const url = window.URL.createObjectURL(new Blob([response.data]))
      const link = document.createElement('a')
      link.href = url
      link.download = `interview-questions-${userId}.xlsx`
      document.body.appendChild(link)
      link.click()
      link.remove()
      window.URL.revokeObjectURL(url)
    } catch {
      setError('Could not export Excel.')
    }
  }

  const importExcel = async (event) => {
    const file = event.target.files?.[0]
    if (!file) return

    const formData = new FormData()
    formData.append('file', file)

    try {
      const response = await api.post(`/users/${userId}/excel/import`, formData, {
        headers: { 'Content-Type': 'multipart/form-data' },
      })
      setError(response.data)
      await loadQuestions()
    } catch (e) {
      setError(e.response?.data?.message || 'Could not import Excel.')
    } finally {
      event.target.value = ''
    }
  }

  const visibleQuestions = useMemo(() => {
    if (filter === 'ALL') return questions
    return questions.filter((q) => q.status === filter)
  }, [questions, filter])

  const completed = questions.filter((q) => q.status !== 'NOT_STARTED').length
  const progress = questions.length ? Math.round((completed / questions.length) * 100) : 0

  return (
    <main className="container">
      <div className="topbar">
        <Link to="/" className="back">← Candidates</Link>
        <div className="excel-actions">
          <button onClick={exportExcel}>Export Excel</button>
          <button className="secondary" onClick={() => fileRef.current?.click()}>
            Import Excel
          </button>
          <input
            ref={fileRef}
            type="file"
            accept=".xlsx,.xls"
            hidden
            onChange={importExcel}
          />
        </div>
      </div>

      <section className="dashboard-header">
        <div>
          <p className="eyebrow">Preparation dashboard</p>
          <h1>Your Questions</h1>
          <p className="subtitle">Mark each question as you progress through preparation.</p>
        </div>
        <div className="progress-card">
          <strong>{progress}%</strong>
          <span>progress</span>
        </div>
      </section>

      {error && <p className="message">{error}</p>}

      <section className="card">
        <h2>Add Question</h2>
        <form className="question-form" onSubmit={addQuestion}>
          <input
            value={text}
            onChange={(e) => setText(e.target.value)}
            placeholder="e.g. Explain HashMap internally in Java"
            maxLength={1000}
          />
          <button type="submit">Add Question</button>
        </form>
      </section>

      <section className="card">
        <div className="section-heading">
          <h2>Questions</h2>
          <select value={filter} onChange={(e) => setFilter(e.target.value)}>
            <option value="ALL">All</option>
            {statuses.map((status) => (
              <option key={status.value} value={status.value}>{status.label}</option>
            ))}
          </select>
        </div>

        {visibleQuestions.length === 0 ? (
          <div className="empty">No questions in this view.</div>
        ) : (
          <div className="question-list">
            {visibleQuestions.map((question, index) => (
              <article className="question-row" key={question.id}>
                <div className="question-number">{index + 1}</div>
                <div className="question-text">{question.questionText}</div>

                <div className="status-options">
                  {statuses.map((status) => (
                    <label key={status.value} className="radio-label">
                      <input
                        type="radio"
                        name={`question-${question.id}`}
                        checked={question.status === status.value}
                        onChange={() => updateStatus(question, status.value)}
                      />
                      {status.label}
                    </label>
                  ))}
                </div>

                <button
                  className="danger-link"
                  onClick={() => deleteQuestion(question.id)}
                >
                  Delete
                </button>
              </article>
            ))}
          </div>
        )}
      </section>

      <footer>
        Interview Prep Tracker V1
      </footer>
    </main>
  )
}
