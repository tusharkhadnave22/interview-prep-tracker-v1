import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import api from '../api'

export default function Home() {
  const [users, setUsers] = useState([])
  const [name, setName] = useState('')
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  const loadUsers = async () => {
    try {
      setLoading(true)
      const response = await api.get('/users')
      setUsers(response.data)
    } catch {
      setError('Could not load users. Check whether the backend is running.')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    loadUsers()
  }, [])

  const addUser = async (event) => {
    event.preventDefault()
    if (!name.trim()) return

    try {
      await api.post('/users', { name: name.trim() })
      setName('')
      setError('')
      await loadUsers()
    } catch (e) {
      setError(e.response?.data?.message || 'Could not add user.')
    }
  }

  const deleteUser = async (id) => {
    if (!window.confirm('Delete this user and all their questions?')) return

    try {
      await api.delete(`/users/${id}`)
      await loadUsers()
    } catch {
      setError('Could not delete user.')
    }
  }

  return (
    <main className="container">
      <header className="hero">
        <div>
          <p className="eyebrow">V1</p>
          <h1>Interview Prep Tracker</h1>
          <p className="subtitle">
            Keep your interview questions organized and know exactly what to prepare.
          </p>
        </div>
      </header>

      <section className="card">
        <h2>Add Candidate</h2>
        <form className="add-form" onSubmit={addUser}>
          <input
            value={name}
            onChange={(e) => setName(e.target.value)}
            placeholder="Enter name"
            maxLength={100}
          />
          <button type="submit">Add</button>
        </form>
        {error && <p className="error">{error}</p>}
      </section>

      <section>
        <div className="section-heading">
          <h2>Candidates</h2>
          <span>{users.length}</span>
        </div>

        {loading ? (
          <p>Loading...</p>
        ) : users.length === 0 ? (
          <div className="empty">No candidates yet. Add your first one above.</div>
        ) : (
          <div className="user-grid">
            {users.map((user) => (
              <div className="user-card" key={user.id}>
                <Link to={`/users/${user.id}`} className="user-link">
                  <div className="avatar">{user.name.charAt(0).toUpperCase()}</div>
                  <div>
                    <h3>{user.name}</h3>
                    <p>{user.questionCount} questions</p>
                  </div>
                </Link>
                <button className="danger-link" onClick={() => deleteUser(user.id)}>
                  Delete
                </button>
              </div>
            ))}
          </div>
        )}
      </section>
    </main>
  )
}
