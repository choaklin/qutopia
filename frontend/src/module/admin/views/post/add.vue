<template>
    <div>
        <!-- workspace -->
        <div class="half-range">
            <div class="edit-head">
                <input id="title" class="title" type="text" name="title" placeholder="无标题博客" value="" maxlength="100">
                <div>
                    <textarea class="abstract" name="abstracts" placeholder="无摘要" value="" maxlength="300"></textarea>
                </div>
                <div id="uploader" class="wu-example">
                    <!--用来存放文件信息-->
                    <div class="btns">
                        <div id="picker">选择文章缩略图</div>
                    </div>
                </div>
            </div>

            <!-- 编辑部分 -->
            <div class="edit-body">
                <!-- 文章选项 -->
                <div class="edit-select">
                    <div class="edit-tags edit-panel">
                        <div class="row-left">标签：</div>
                        <div class="row-right">
                            <div class="tags"><span class="tags-item"><span class="tag-name">sasa</span><span class="close">×</span></span></div>
                            <div class="add-tags-box">
                                <input type="text" class="input-tags" placeholder="请输入关键词，用逗号隔开，最多5个哦" maxlength="20" style="display: inline-block;">
                                <ul class="autoPrompt" style="display: none;"></ul>
                            </div>
                        </div>
                    </div>
                    <div class="edit-panel edit-list">
                        <ul>
                            <li class="edit-row is-original">
                                <div class="row-left">
                                    文章类型:
                                </div>
                                <div class="row-right">
                                    <input type="radio" name="type" id="isOriginal" value="1" checked="true">
                                    <span class="radio-label">
								<label class="radio" for="isOriginal"></label>
								<label for="isOriginal">原创</label>
							</span>
                                    <input type="radio" name="type" id="notOriginal" value="4">
                                    <span class="radio-label">
								<label class="radio" for="notOriginal"></label>
								<label for="notOriginal">转帖</label>
								<input type="text" class="ext-url" name="origin_url" placeholder="输入转帖的原文链接" value="" maxlength="100">
							</span>
                                </div>
                            </li>
                            <li class="edit-row">
                                <div class="row-left">
                                    <label for="isPrivate" class="label-desc">是否对所有人可见:</label>
                                </div>
                                <div class="row-right">
                                    <input type="checkbox" name="privacy" id="isPrivate" value="0">
                                    <label class="offon active" for="isPrivate"></label>
                                </div>
                            </li>
                            <li class="edit-row">
                                <div class="row-left">
                                    <label for="isComment" class="label-desc">是否允许评论:</label>
                                </div>
                                <div class="row-right">
                                    <input type="checkbox" name="deny_comment" id="isComment" value="0">
                                    <label class="offon active" for="isComment"></label>
                                </div>
                            </li>
                            <li class="edit-row">
                                <div class="row-left">
                                    <label for="isStickTop" class="label-desc">是否置顶:</label>
                                </div>
                                <div class="row-right">
                                    <input type="checkbox" name="as_top" id="isStickTop" value="0">
                                    <label class="offon " for="isStickTop"></label>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <!-- 编辑器 -->
                <div class="edit-box">
                    <textarea name="content" id="kindeditor" style="width: 700px; height: 500px;"></textarea>
                </div>
            </div>

            <!-- 控制按钮部分 -->
            <div class="edit-ctrl">
                <button id="preview">预览</button>
                <button name="保存">保存</button>
            </div>
        </div>

        <!-- preview -->
        <div class="half-range">
            <article class="post">
                <div id="thumbnail" class="thumbnail"></div>
                <div class="meta"></div>
                <div id="preview_content" class="content"></div>
            </article>
        </div>
    </div>
</template>
