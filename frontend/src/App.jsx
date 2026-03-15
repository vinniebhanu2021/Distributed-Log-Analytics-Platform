
import { useState, useEffect } from 'react';
import { Activity, AlertTriangle, Send, Server, Shield, Zap } from 'lucide-react';
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer, Cell } from 'recharts';

function App() {
  const [metrics, setMetrics] = useState({
    errorsPerService: [],
    logCounts: [],
    topWarnings: []
  });

  const [formData, setFormData] = useState({
    serviceName: 'payment-service',
    level: 'INFO',
    message: 'User transaction processed successfully'
  });

  const [isSending, setIsSending] = useState(false);

  // Poll Metrics every 2 seconds
  useEffect(() => {
    const fetchMetrics = async () => {
      try {
        const [errorsRes, countsRes] = await Promise.all([
          fetch('http://localhost:8083/metrics/errors-per-service'),
          fetch('http://localhost:8083/metrics/log-count')
        ]);

        const errorsData = await errorsRes.json();
        const countsData = await countsRes.json();

        // Transform data for charts
        const errorChartData = Object.entries(errorsData[0] || {}).map(([name, count]) => ({
          name,
          count
        }));

        const countChartData = Object.entries(countsData[0] || {}).map(([name, count]) => ({
          name,
          count
        }));

        setMetrics(prev => ({ ...prev, errorsPerService: errorChartData, logCounts: countChartData }));
      } catch (err) {
        console.error("Failed to fetch metrics", err);
      }
    };

    fetchMetrics();
    const interval = setInterval(fetchMetrics, 2000);
    return () => clearInterval(interval);
  }, []);

  const handleSendLog = async (e) => {
    e.preventDefault();
    setIsSending(true);
    try {
      await fetch('http://localhost:8081/api/test/log', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData)
      });
      // Reset message slightly to indicate success/allow new input
      setFormData(prev => ({ ...prev, message: `New event at ${new Date().toLocaleTimeString()}` }));
    } catch (err) {
      console.error("Failed to send log", err);
      alert("Failed to send log: Check Ingestion Service");
    } finally {
      setIsSending(false);
    }
  };

  return (
    <div className="dashboard-container">
      <header className="header">
        <div className="title">
          <Zap size={28} color="#60a5fa" />
          <span>LogAnalytics<span style={{ color: '#a1a1aa', fontWeight: 400 }}>AI</span></span>
        </div>
        <div style={{ color: 'var(--success)', display: 'flex', alignItems: 'center', gap: '0.5rem', fontSize: '0.9rem' }}>
          <span className="badge badge-INFO">● System Online</span>
        </div>
      </header>

      <div className="grid">
        {/* Metric Card 1: Total Activity */}
        <div className="card">
          <div className="card-header">
            <span className="card-title">Total Logs Processed</span>
            <Activity size={20} color="var(--accent)" />
          </div>
          <div className="stat-value">
            {metrics.logCounts.reduce((acc, curr) => acc + curr.count, 0)}
          </div>
          <div className="label" style={{ marginTop: '1rem' }}>Real-time Ingestion Stream</div>
        </div>

        {/* Metric Card 2: Error Rate */}
        <div className="card">
          <div className="card-header">
            <span className="card-title">Critical Errors</span>
            <AlertTriangle size={20} color="var(--error)" />
          </div>
          <div className="stat-value" style={{ background: 'linear-gradient(to bottom, #fca5a5, #ef4444)', WebkitBackgroundClip: 'text', WebkitTextFillColor: 'transparent' }}>
            {metrics.errorsPerService.reduce((acc, curr) => acc + curr.count, 0)}
          </div>
          <div className="label" style={{ marginTop: '1rem' }}>Anomalies Detected</div>
        </div>
      </div>

      <div className="grid" style={{ gridTemplateColumns: '2fr 1fr' }}>
        {/* Chart Section */}
        <div className="card">
          <div className="card-header">
            <span className="card-title">Error Distribution by Service</span>
          </div>
          <div style={{ height: '300px', width: '100%' }}>
            <ResponsiveContainer width="100%" height="100%">
              <BarChart data={metrics.errorsPerService}>
                <XAxis dataKey="name" stroke="#52525b" tick={{ fill: '#a1a1aa' }} />
                <YAxis stroke="#52525b" tick={{ fill: '#a1a1aa' }} />
                <Tooltip
                  contentStyle={{ backgroundColor: '#18181b', borderColor: '#27272a', color: '#fff' }}
                  itemStyle={{ color: '#fff' }}
                  cursor={{ fill: 'rgba(255,255,255,0.05)' }}
                />
                <Bar dataKey="count" fill="#ef4444" radius={[4, 4, 0, 0]}>
                  {metrics.errorsPerService.map((entry, index) => (
                    <Cell key={`cell-${index}`} fill={['#ef4444', '#f97316', '#eab308'][index % 3]} />
                  ))}
                </Bar>
              </BarChart>
            </ResponsiveContainer>
          </div>
        </div>

        {/* Log Injector Form */}
        <div className="card" style={{ borderColor: 'var(--accent)' }}>
          <div className="card-header">
            <span className="card-title">Simulate Log Event</span>
            <Server size={20} color="var(--text-muted)" />
          </div>

          <form onSubmit={handleSendLog}>
            <div className="input-group">
              <label className="label">Target Service</label>
              <select
                className="input-control"
                value={formData.serviceName}
                onChange={e => setFormData({ ...formData, serviceName: e.target.value })}
              >
                <option value="payment-service">payment-service</option>
                <option value="user-service">user-service</option>
                <option value="auth-service">auth-service</option>
              </select>
            </div>

            <div className="input-group">
              <label className="label">Log Level</label>
              <select
                className="input-control"
                value={formData.level}
                onChange={e => setFormData({ ...formData, level: e.target.value })}
              >
                <option value="INFO">INFO</option>
                <option value="WARN">WARN</option>
                <option value="ERROR">ERROR</option>
              </select>
            </div>

            <div className="input-group">
              <label className="label">Message Payload</label>
              <input
                type="text"
                className="input-control"
                value={formData.message}
                onChange={e => setFormData({ ...formData, message: e.target.value })}
              />
            </div>

            <button type="submit" className="btn" disabled={isSending}>
              <Send size={18} />
              {isSending ? 'Injecting...' : 'Inject Log'}
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default App;
