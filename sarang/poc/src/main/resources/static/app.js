const cafeList = document.querySelector('#cafe-list');
const userIdInput = document.querySelector('#user-id');
const result = document.querySelector('#result');

async function loadCafes() {
    const response = await fetch('/api/cafes');
    const cafes = await response.json();
    cafeList.innerHTML = cafes.map((cafe, index) => `
        <article class="cafe-card">
            <div>
                <span class="cafe-number">0${index + 1}</span>
                <h3>${cafe.name}</h3>
                <p class="address">${cafe.address}</p>
            </div>
            <button type="button" data-cafe-id="${cafe.id}">즐겨찾기 등록</button>
        </article>
    `).join('');
    cafeList.querySelectorAll('button').forEach(button => button.addEventListener('click', registerFavorite));
}

async function registerFavorite(event) {
    const button = event.currentTarget;
    const userId = Number(userIdInput.value);
    const cafeId = Number(button.dataset.cafeId);
    button.disabled = true;
    result.textContent = '';

    const response = await fetch('/api/favorites', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userId, cafeId })
    });
    const body = await response.json().catch(() => response.text());
    result.textContent = response.ok ? body : body.message;
    button.disabled = false;
}

loadCafes().catch(() => { result.textContent = '카페 목록을 불러오지 못했습니다.'; });
