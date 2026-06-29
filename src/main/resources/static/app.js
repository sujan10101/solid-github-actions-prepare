const API = 'http://localhost:8080/api/todos';

let todos = [];
let selectedId = null;

document.addEventListener('DOMContentLoaded', () => {
  fetchTodos();
  updatePutPreview();
  updatePatchPreview();
});

// ── GET ───────────────────────────────────────────────
async function fetchTodos() {
  const res = await fetch(API);
  todos = await res.json();
  renderTodos();
}

function renderTodos() {
  const list = document.getElementById('todoList');

  if (todos.length === 0) {
    list.innerHTML = '<p class="empty">No todos yet. Add one above!</p>';
    return;
  }

  list.innerHTML = todos.map(todo => `
    <div class="todo-item ${todo.id === selectedId ? 'selected' : ''}" onclick="selectTodo(${todo.id})">
      <div class="todo-left">
        <span class="todo-id">#${todo.id}</span>
        <div>
          <div class="todo-title ${todo.completed ? 'done' : ''}">${todo.title}</div>
          ${todo.description ? `<div class="todo-desc">${todo.description}</div>` : ''}
        </div>
      </div>
      <div class="todo-right">
        ${todo.completed ? '<span class="done-badge">Done</span>' : ''}
        <button class="btn-delete" onclick="event.stopPropagation(); deleteTodo(${todo.id})">
          <span class="badge delete">DELETE</span>
        </button>
      </div>
    </div>
  `).join('');
}

// ── POST ──────────────────────────────────────────────
async function createTodo() {
  const title = document.getElementById('addTitle').value.trim();
  const description = document.getElementById('addDesc').value.trim();

  if (!title) { showToast('Title is required'); return; }

  const res = await fetch(API, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ title, description, completed: false })
  });

  if (res.ok) {
    document.getElementById('addTitle').value = '';
    document.getElementById('addDesc').value = '';
    showToast('Todo created!');
    fetchTodos();
  } else {
    const err = await res.json();
    showToast(err.title || 'Error');
  }
}

// ── PUT — replaces all fields ─────────────────────────
async function updateTodo() {
  const id = selectedId || document.getElementById('putId').value;
  if (!id) { showToast('Click a todo from the list first'); return; }

  const title = document.getElementById('putTitle').value.trim();
  const description = document.getElementById('putDesc').value.trim();
  const completed = document.getElementById('putCompleted').checked;

  if (!title) { showToast('Title is required for PUT'); return; }

  const res = await fetch(`${API}/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ title, description, completed })
  });

  if (res.ok) {
    showToast('PUT done — all 3 fields replaced!');
    fetchTodos();
  } else {
    const err = await res.json();
    showToast(err.error || 'Error');
  }
}

// ── PATCH — only toggles completed ───────────────────
async function patchTodo() {
  const id = selectedId || document.getElementById('patchId').value;
  if (!id) { showToast('Click a todo from the list first'); return; }

  const description = document.getElementById('patchDesc').value;

  const res = await fetch(`${API}/${id}`, {
    method: 'PATCH',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ description })
  });

  if (res.ok) {
    showToast('PATCH done — only description updated!');
    fetchTodos();
  } else {
    const err = await res.json();
    showToast(err.error || 'Error');
  }
}

// ── DELETE ────────────────────────────────────────────
async function deleteTodo(id) {
  if (!confirm(`Delete todo #${id}?`)) return;

  const res = await fetch(`${API}/${id}`, { method: 'DELETE' });

  if (res.ok) {
    if (selectedId === id) {
      selectedId = null;
      clearForms();
    }
    showToast(`Todo #${id} deleted!`);
    fetchTodos();
  }
}

// ── Click todo → prefill both forms ───────────────────
function selectTodo(id) {
  const todo = todos.find(t => t.id === id);
  if (!todo) return;

  selectedId = id;

  // PUT — prefill all fields
  document.getElementById('putId').value = `#${todo.id} — ${todo.title}`;
  document.getElementById('putTitle').value = todo.title;
  document.getElementById('putDesc').value = todo.description || '';
  document.getElementById('putCompleted').checked = todo.completed;

  // PATCH — prefill only description
  document.getElementById('patchId').value = `#${todo.id} — ${todo.title}`;
  document.getElementById('patchDesc').value = todo.description || '';

  updatePutPreview();
  updatePatchPreview();
  renderTodos();
}

function clearForms() {
  ['putTitle', 'putDesc', 'patchDesc'].forEach(id => document.getElementById(id).value = '');
  document.getElementById('putCompleted').checked = false;
  updatePutPreview();
  updatePatchPreview();
}

// ── Live JSON previews ────────────────────────────────
function updatePutPreview() {
  const body = {
    title: document.getElementById('putTitle').value || '',
    description: document.getElementById('putDesc').value || '',
    completed: document.getElementById('putCompleted').checked
  };
  document.getElementById('putPreview').textContent = JSON.stringify(body, null, 2);
}

function updatePatchPreview() {
  const body = {
    description: document.getElementById('patchDesc').value || ''
  };
  document.getElementById('patchPreview').textContent = JSON.stringify(body, null, 2);
}

// ── Toast ─────────────────────────────────────────────
function showToast(message) {
  const toast = document.getElementById('toast');
  toast.textContent = message;
  toast.classList.add('show');
  setTimeout(() => toast.classList.remove('show'), 2500);
}
