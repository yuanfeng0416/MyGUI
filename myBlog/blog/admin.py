from django.contrib import admin
from django.contrib import admin

from .models import Category, Tag, Blog

admin.site.register([Category, Tag, Blog])
