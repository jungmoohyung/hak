<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>




	<!-- Links (sit on top) -->
	<div class="w3-top">
		<div class="w3-row w3-padding w3-black">

			<div class="w3-col s13">
				<a href="#" class="w3-button w3-block w3-black">HOME</a>
			</div>
			<div class="w3-col s13">
				<a href="#about" class="w3-button w3-block w3-black">ABOUT</a>
			</div>
			<div class="w3-col s13">
				<a href="#menu" class="w3-button w3-block w3-black">MENU</a>
			</div>
			<div class="w3-col s13">
				<a href="#where" class="w3-button w3-block w3-black">WHERE</a>
			</div>
			<div class="w3-col s13">
				<a href="/board/memReg2Board.jsp" class="w3-button w3-block w3-black">BOARD</a>
			</div>

		</div>
	</div>

	<!-- Header with image -->
	<header class="bgimg w3-display-container w3-grayscale-min" id="home">
		<div
			class="w3-display-bottomleft w3-center w3-padding-large w3-hide-small">
			<span class="w3-tag">대충 열었다 닫습니다. 나도몰라요.</span>
		</div>
		<div class="w3-display-middle w3-center">
			<span class="w3-text-white"
				style="font-size: 90px; font-family: 'Blaka', cursive;">chick<br>by<br>chick
			</span>
		</div>
		<div class="w3-display-bottomright w3-center w3-padding-large">
			<span class="w3-text-white">학원 바로 옆입니다.</span>
		</div>
	</header>



	<!-- Add a background color and large text to the whole page -->
	<div>

		<!-- About Container -->
		<div class="w3-container" id="about">
			<div class="w3-content" style="max-width: 700px">
				<h5 class="w3-center w3-padding-64">
					<span class="w3-tag w3-wide">치킨위주 햄버거집 소개</span>
				</h5>
				<p>학원 옆에 있구요. 먹을만해요. 치킨이 다 들어있구요. 가격도 나쁘지 않아요.</p>
				<p>뭐 대충 설명 다 했어요.</p>
				<div class="w3-panel w3-leftbar w3-light-grey"
					style="position: sticky; top: 5%">
					<p>
						<i>"인생은 B와 D사이의 C다. birth 와 death 그리고 chicken."</i>
					</p>
					<p>인터넷의 누군가가..</p>
				</div>
				<img src="img/burger.png" style="width: 100%; max-width: 1000px"
					class="w3-margin-top">
				<p>
					<strong>Opening hours:</strong>학원 점심시간엔 항상 열려있던대요?
				</p>
				<p>
					<strong>Address:</strong>학원 바로 옆입니다.
				</p>
			</div>
		</div>

		<!-- Menu Container -->
		<div class="w3-container" id="menu">
			<div class="w3-content" style="max-width: 700px">

				<h5 class="w3-center w3-padding-48">
					<span class="w3-tag w3-wide">먹을거 정보</span>
				</h5>

				<div class="w3-row w3-center w3-card w3-padding">
					<a href="javascript:void(0)" onclick="openMenu(event, 'burger');"
						id="myLink">
						<div class="w3-col s6 tablink">burger</div>
					</a> <a href="javascript:void(0)" onclick="openMenu(event, 'Drinks');">
						<div class="w3-col s6 tablink">Drink</div>
					</a> <a href="javascript:void(0)" onclick="openMenu(event, 'toffjem');">
						<div class="w3-col s6 tablink">샐러드</div>
					</a> <a href="javascript:void(0)"
						onclick="openMenu(event, 'riceball');">
						<div class="w3-col s6 tablink">riceball</div>
					</a>
				</div>

				<div id="burger" class="w3-container menu w3-card">
					<p class="sample">누르면 샘플 화면이 나옵니다.(화면과 다를수 있습니다.)</p>
					<details>
						<summary style="font-size: 20px">그릴라이트 치킨버거</summary>
						<img
							src="https://search.pstatic.net/common/?autoRotate=true&quality=95&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20211219_120%2F1639873969297PflrC_JPEG%2FB7ovhW1XOxivi5dRx14mNty_KykYa1OQvL6KHdux6yctHCKW6QU-qYddFMCkZ4kc.jpg&type=h166">
					</details>

					<details>
						<summary style="font-size: 20px">아보카도 치킨버거</summary>
						<img
							src="https://search.pstatic.net/common/?autoRotate=true&quality=95&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20211219_96%2F1639873969391luB7S_JPEG%2FB7ovhW1XOxivi5dRx14mNj0A3gIrC69FYoG-2s-sJm0LsQwW-2OW58O5vkP5vmAT.jpg&type=h166">
					</details>

					<details>
						<summary style="font-size: 20px">베이컨 치즈 치킨버거</summary>
						<img
							src="https://search.pstatic.net/common/?autoRotate=true&quality=95&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20211220_279%2F1639964312832IuJQx_JPEG%2FtLX_g_-jfIGNeCXcqIJBsL7O7Rz-qQZX01Rk5FhI6Gc%253D.jpg&type=h166">
					</details>

					<details>
						<summary style="font-size: 20px">킹프론 버거</summary>
						<img
							src="https://search.pstatic.net/common/?autoRotate=true&quality=95&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20211220_146%2F1639962970520d7JyA_JPEG%2FpmUATtk0M7lflLC111JcnLuWrml3WdXcS2SOMyI1jVM%253D.jpg&type=h166">
					</details>


				</div>

				<div id="Drinks" class="w3-container menu w3-card">
					<p class="sample">누르면 샘플 화면이 나옵니다.(화면과 다를수 있습니다.)</p>
					<details>
						<summary style="font-size: 20px">콜라355ml</summary>
						<img
							src="https://search.pstatic.net/common/?autoRotate=true&quality=95&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20220215_295%2F16448961609452KwUu_JPEG%2F2dSu9T674OTr6fMUZmzKLGcnpM8KYLdOVqAxUr62UaE%253D.jpg&type=h166">
					</details>

					<details>
						<summary style="font-size: 20px">스프라이트355ml</summary>
						<img
							src="https://search.pstatic.net/common/?autoRotate=true&quality=95&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20220215_222%2F1644896207647H10Vj_JPEG%2FNJKygRT8_k7jS1LMQLxu9_4EgFnz_KKTQI5_C79FxnU%253D.jpg&type=h166">
					</details>

					<details>
						<summary style="font-size: 20px">콜라제로355ml</summary>
						<img
							src="https://search.pstatic.net/common/?autoRotate=true&quality=95&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20220215_72%2F1644896207536pCxjm_JPEG%2FIoEL-w9RiWGRzwDUPUVZsqUCgpW1Ukpo4JlUnXag3ds%253D.jpg&type=h166">
					</details>

					<details>
						<summary style="font-size: 20px">물</summary>
						<img
							src="https://search.pstatic.net/common/?autoRotate=true&quality=95&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20220215_107%2F1644887473949BFtj2_JPEG%2Fd7X8fmGhpUAAxZIDZBXPaGbH1o5xNyi9Hh0EahNd9Z4%253D.jpg&type=h166">
					</details>




				</div>

				<div id="toffjem" class="w3-container menu w3-card">
					<p class="sample">누르면 샘플 화면이 나옵니다.(화면과 다를수 있습니다.)</p>
					<details>
						<summary style="font-size: 20px">그릴라이트 치킨샐러드</summary>
						<img
							src="https://search.pstatic.net/common/?autoRotate=true&quality=95&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20220419_104%2F16503273261522XFoa_JPEG%2FcDjRCMLZDW0GOwvomwtoSWvB5zYM5-AeMh02K2qiZgA%253D.jpg&type=h166">
					</details>

					<details>
						<summary style="font-size: 20px">크리스피텐더 치킨샐러드</summary>
						<img
							src="https://search.pstatic.net/common/?autoRotate=true&quality=95&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20220419_279%2F1650327313922xbEpQ_JPEG%2F5EAjRkMXI_o3m3U4eRpVxBenCG45vNzGnriPReV65Hw%253D.jpg&type=h166">
					</details>

					<details>
						<summary style="font-size: 20px">킹프론 샐러드</summary>
						<img
							src="https://search.pstatic.net/common/?autoRotate=true&quality=95&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20220419_119%2F1650327294645BFKs7_JPEG%2F_WuO6dk7nRAS8qcfCdTHK2h5fHII2LKV9hm3H37ydIo%253D.jpg&type=h166">
					</details>

				</div>

				<div id="riceball" class="w3-container menu w3-card">
					<p class="sample">누르면 샘플 화면이 나옵니다.(화면과 다를수 있습니다.)</p>
					<details>
						<summary style="font-size: 20px">그릴라이트 치킨라이스볼</summary>
						<img
							src="https://search.pstatic.net/common/?autoRotate=true&quality=95&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20211220_103%2F1639973652518DXazK_JPEG%2FoggXyvPFuOCOBkCtRozXEz9vQBTm8T0u_7vBtZDl7U8%253D.jpg&type=h166">
					</details>

					<details>
						<summary style="font-size: 20px">킹프론 치킨라이스볼</summary>
						<img
							src="https://search.pstatic.net/common/?autoRotate=true&quality=95&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20220512_249%2F1652361290800cxLkG_JPEG%2FhqnlTxzDdQJYVxcJ0ahyjou6VlwNbHdr1yNlCYHfv1g%253D.jpg&type=h166">
					</details>

					<details>
						<summary style="font-size: 20px">아보카도 치킨라이스볼</summary>
						<img
							src="https://search.pstatic.net/common/?autoRotate=true&quality=95&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20220512_100%2F1652361384469Ir5GH_JPEG%2F3O_xhRhOtnUdsRlGTW4dFSzfzV48QYf1eqYfpCNWaUY%253D.jpg&type=h166">
					</details>

					<details>
						<summary style="font-size: 20px">킹프론 아보카도 치킨라이스볼</summary>
						<img
							src="https://search.pstatic.net/common/?autoRotate=true&quality=95&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20220512_200%2F1652361384463h7oVD_JPEG%2FUu0Ni6WBa0PfYeqZMY0mKfxNpfCDH5PTf2QWM7w7A2A%253D.jpg&type=h166">
					</details>

				</div>


				<img
					src="https://search.pstatic.net/common/?autoRotate=true&quality=95&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20220517_242%2F16527591520411C4NN_JPEG%2FNteHUsNDlhzdtRD3zXrEgiYgDM3zcTk4kd0QfhX2zD8%253D.jpg&type=h166"
					style="width: 100%; max-width: 1000px; margin-top: 32px;">
			</div>
		</div>

		<!-- Contact/Area Container -->
		<div class="w3-container" id="where" style="padding-bottom: 32px;">
			<div class="w3-content" style="max-width: 700px">
				<h5 class="w3-center w3-padding-48">
					<span class="w3-tag w3-wide">위치는 여기있어요!! 어설퍼도 봐줘요</span>
				</h5>
				<p
					style="font-family: 'Nanum Pen Script', 'cursive'; font-size: 30px">포토샵
					잘 못써서 그냥 그림판으로 때웠어요.</p>
				<img src="img/지도.png" class="w3-image">
				<p>
					<span class="w3-tag">존맛!</span> 이동네 패스트푸드 1위집이래요 한번 드셔보시면 후회는 안합니다.
					라이스볼도 응근 배불러요.
				</p>
				<p>
					<strong>친구해요!</strong>밑에 이름 전화번호 적으시면 저한테 연락이와요!
				</p>
				<!-- <form name="form1" action="list.jsp" method="post">
	<p> ID: <input type="text" name="Name"><br><br>
	<p> date <input type="text" name="date"><br><br>
	<p> Message <input type="text" name="Message"><br><br>
	password <input type="password" name="Phonnumber"><br><br>
	<input type="submit" name="formbutton1" value="보내기">&nbsp;</p>
</form> -->
				<form action="list.jsp" target="_blank" method="post" onsubmit="return formCheck()">
					<p>
						<input class="w3-input w3-padding-16 w3-border" type="text" placeholder="Name" name="Name">
					</p>
					<p>
						<input class="w3-input w3-padding-16 w3-border" type="number" placeholder="Phonnumber" name="Phonnumber">
					</p>
					<p>
						<input class="w3-input w3-padding-16 w3-border"	type="datetime-local" placeholder="Date and time" required	name="date" value="2020-11-16T20:00">
					</p>
					<p>
						<input class="w3-input w3-padding-16 w3-border" type="text"
							placeholder="하실말씀 있으세요?" name="Message">
					</p>
					<p>
						<button class="w3-button w3-black" type="submit">누르면 확인
							가능해요</button>
					</p>
				</form>
			</div>
		</div>

		<!-- End page content -->
	</div>

	<!-- Footer -->
	<footer class="w3-center w3-light-grey w3-padding-48 w3-large">
		<p>
			Powered by <a href="https://www.w3schools.com/w3css/default.asp"
				title="W3.CSS" target="_blank" class="w3-hover-text-green">w3.css</a>
		</p>
	</footer>
	
	
<%@ include file = "loginchick.jsp" %>
	

	<script>
		// Tabbed Menu
		function openMenu(evt, menuName) {
			var i, x, tablinks;
			x = document.getElementsByClassName("menu");
			for (i = 0; i < x.length; i++) {
				x[i].style.display = "none";
			}
			tablinks = document.getElementsByClassName("tablink");
			for (i = 0; i < x.length; i++) {
				tablinks[i].className = tablinks[i].className.replace(
						" w3-dark-grey", "");
			}
			document.getElementById(menuName).style.display = "block";
			evt.currentTarget.firstElementChild.className += " w3-dark-grey";

		}
		document.getElementById("myLink").click();

	</script>

</body>
</html>
