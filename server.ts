import express from 'express';
import path from 'path';

const app = express();
const PORT = 3000;

// Serve static files from the frontend directory
app.use(express.static(path.join(process.cwd(), 'frontend')));

// Root route sends frontend/index.html
app.get('/', (req, res) => {
  res.sendFile(path.join(process.cwd(), 'frontend', 'index.html'));
});

app.listen(PORT, '0.0.0.0', () => {
  console.log(`CampusConnect preview server running on port ${PORT}`);
});
